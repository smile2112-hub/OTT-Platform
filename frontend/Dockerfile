# Authors:  Aakanksha Ashok Hedau  <aakanksha0499@gmail.com>
#           Mahesh Ganesh Kajale   <maheshkajale2112@gmail.com>
# Project:  FlowFlix OTT Platform
# ─────────────────────────────────────────────
# Stage 1 – Build the React app
# ─────────────────────────────────────────────
FROM node:18-alpine AS build

WORKDIR /app

# Install dependencies first (better layer caching)
COPY package*.json ./
RUN npm ci --silent

# Copy source and build
COPY . .
RUN npm run build

# ─────────────────────────────────────────────
# Stage 2 – Serve with Nginx
# ─────────────────────────────────────────────
FROM nginx:alpine

# Remove default nginx static assets
RUN rm -rf /usr/share/nginx/html/*

# Copy the React build output
COPY --from=build /app/build /usr/share/nginx/html

# Custom nginx config to support React Router (SPA fallback)
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
