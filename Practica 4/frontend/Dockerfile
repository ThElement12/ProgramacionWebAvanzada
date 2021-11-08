FROM node:12-alpine AS builder

WORKDIR /app

COPY package.json ./

RUN npm install 

ARG REACT_APP_WEBSOCKET
ENV REACT_APP_WEBSOCKET=${REACT_APP_WEBSOCKET}

COPY . ./

ENTRYPOINT ["npm", "start"]