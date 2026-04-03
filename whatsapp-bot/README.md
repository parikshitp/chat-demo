# WhatsApp Bot

A simple WhatsApp bot built with Node.js and Express that responds with **"Hi"** to any incoming message, using the [WhatsApp Business Cloud API](https://developers.facebook.com/docs/whatsapp/cloud-api).

## Prerequisites

- **Node.js** (v16 or higher)
- A **Meta Developer Account** with a WhatsApp Business App
- A publicly accessible URL (use [ngrok](https://ngrok.com/) for local development)

## Setup

### 1. Meta Developer Portal

1. Go to [developers.facebook.com](https://developers.facebook.com/) and create a new app (type: **Business**).
2. Add the **WhatsApp** product to your app.
3. In **WhatsApp → API Setup**, note your:
   - **Phone Number ID**
   - **Temporary Access Token** (or generate a permanent one via System Users)

### 2. Install & Configure

```bash
cd whatsapp-bot
npm install
cp .env.example .env
```

Edit `.env` with your credentials:

```env
PHONE_NUMBER_ID=your_phone_number_id
WHATSAPP_ACCESS_TOKEN=your_access_token
VERIFY_TOKEN=my_secret_verify_token
PORT=3000
```

### 3. Run the Bot

```bash
npm start
```

### 4. Expose with ngrok (for local development)

```bash
ngrok http 3000
```

Copy the HTTPS URL (e.g., `https://abcd1234.ngrok.io`).

### 5. Configure the Webhook in Meta

1. Go to **WhatsApp → Configuration** in the Meta Developer Portal.
2. Set the **Callback URL** to `https://your-ngrok-url/webhook`.
3. Set the **Verify Token** to the same value as `VERIFY_TOKEN` in your `.env`.
4. Subscribe to the **messages** webhook field.

### 6. Test It

Send a message to your WhatsApp Business number from any WhatsApp account. The bot will reply with **"Hi"**.

## API Endpoints

| Method | Path       | Description                          |
|--------|------------|--------------------------------------|
| GET    | `/webhook` | Webhook verification (used by Meta)  |
| POST   | `/webhook` | Receives incoming messages            |
| GET    | `/health`  | Health check                          |

## Project Structure

```
whatsapp-bot/
├── index.js          # Main application
├── package.json      # Dependencies and scripts
├── .env.example      # Environment variable template
├── .gitignore        # Git ignore rules
└── README.md         # This file
```
