const express = require("express");
const axios = require("axios");
require("dotenv").config();

const app = express();
app.use(express.json());

const PORT = process.env.PORT || 3000;
const WHATSAPP_API_URL = "https://graph.facebook.com/v21.0";
const PHONE_NUMBER_ID = process.env.PHONE_NUMBER_ID;
const ACCESS_TOKEN = process.env.WHATSAPP_ACCESS_TOKEN;
const VERIFY_TOKEN = process.env.VERIFY_TOKEN;

/**
 * GET /webhook
 * WhatsApp webhook verification endpoint.
 * Meta sends a GET request with a challenge to verify your webhook URL.
 */
app.get("/webhook", (req, res) => {
  const mode = req.query["hub.mode"];
  const token = req.query["hub.verify_token"];
  const challenge = req.query["hub.challenge"];

  if (mode === "subscribe" && token === VERIFY_TOKEN) {
    console.log("Webhook verified successfully.");
    return res.status(200).send(challenge);
  }

  console.warn("Webhook verification failed. Check your VERIFY_TOKEN.");
  return res.sendStatus(403);
});

/**
 * POST /webhook
 * Receives incoming WhatsApp messages and replies with "Hi".
 */
app.post("/webhook", async (req, res) => {
  const body = req.body;

  if (body.object !== "whatsapp_business_account") {
    return res.sendStatus(404);
  }

  const entries = body.entry || [];
  for (const entry of entries) {
    const changes = entry.changes || [];
    for (const change of changes) {
      const messages = change.value?.messages || [];
      for (const message of messages) {
        const senderPhone = message.from;
        console.log(
          `Received message from ${senderPhone}: ${message.text?.body || "(non-text)"}`
        );
        await sendReply(senderPhone, "Hi");
      }
    }
  }

  // Always return 200 quickly to acknowledge receipt
  return res.sendStatus(200);
});

/**
 * Sends a text reply to the given phone number via the WhatsApp Cloud API.
 */
async function sendReply(to, text) {
  const url = `${WHATSAPP_API_URL}/${PHONE_NUMBER_ID}/messages`;

  try {
    await axios.post(
      url,
      {
        messaging_product: "whatsapp",
        to,
        type: "text",
        text: { body: text },
      },
      {
        headers: {
          Authorization: `Bearer ${ACCESS_TOKEN}`,
          "Content-Type": "application/json",
        },
      }
    );
    console.log(`Replied "Hi" to ${to}`);
  } catch (err) {
    console.error(
      "Failed to send reply:",
      err.response?.data || err.message
    );
  }
}

/**
 * Health check endpoint.
 */
app.get("/health", (_req, res) => {
  res.json({ status: "ok" });
});

app.listen(PORT, () => {
  console.log(`WhatsApp bot is running on port ${PORT}`);
});
