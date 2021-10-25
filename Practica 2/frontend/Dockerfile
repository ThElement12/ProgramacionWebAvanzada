FROM node:12-alpine AS builder

WORKDIR /app

COPY package.json ./

RUN npm install 

ARG REACT_APP_API_URL
ENV REACT_APP_API_URL=${REACT_APP_API_URL}

COPY . ./

ENTRYPOINT ["npm", "start"]