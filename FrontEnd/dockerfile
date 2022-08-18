#build stage
FROM node:lts-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install --save --legacy-peer-deps
COPY . .
RUN npm run build

EXPOSE 8000
CMD ["npm", "run", "serve", "--", "--port", "8000"]

