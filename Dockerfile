FROM tomcat:9

ENV DB_HOST=""
ENV DB_PORT=""
ENV DB_DATABASE=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""

WORKDIR /usr/local/tomcat/webapps/

COPY ./target/arquitectura.war ./ROOT.war