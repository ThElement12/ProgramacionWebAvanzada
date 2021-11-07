FROM node:12-alpine AS builder

WORKDIR /app

COPY package.json ./

RUN npm install 

ARG MQT_ROUTE
ENV MQT_ROUTE=${MQT_ROUTE}

COPY . ./

ENTRYPOINT ["node", "index.js"]