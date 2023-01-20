FROM node:16.17.1-alpine

# создание директории приложения
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . .

# установка зависимостей
RUN npm ci

#сборка
RUN npm run build


CMD [ "node", ".output/server/index.mjs" ]
