FROM node:13.12.0-alpine as build-step
RUN mkdir -p /app
WORKDIR /app
COPY package.json /app

#RUN npm install -g npm@9.4.1
RUN npm install --force
COPY . /app
RUN npm run build --prod
#RUN npm run build

FROM nginx:alpine
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-step /app/dist/Angular12JwtAuth /usr/share/nginx/html
EXPOSE 4200:80
