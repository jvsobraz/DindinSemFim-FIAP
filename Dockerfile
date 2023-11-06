# syntax=docker/dockerfile:1
FROM node:12-alpine
RUN apk add --no-cache python3 g++ make
WORKDIR /app
#WORKDIR /usr/src/app (outro exemplo)
COPY . .
#COPY /app/* /usr/src/app/ (outro exemplo)
RUN yarn install --production
CMD ["node", "src/index.js"]
#CMD node /usr/src/app/index.js (outro exemplo)
EXPOSE 3000