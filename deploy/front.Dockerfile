FROM node:24-alpine AS builder
WORKDIR /workspace

COPY relay-web/package*.json ./
RUN npm ci

COPY relay-web/ ./

RUN npm run build

FROM nginx:alpine
COPY --from=builder /workspace/dist /usr/share/nginx/html

COPY deploy/nginx.conf /etc/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]
