FROM airhacks/wildfly
COPY ./target/admin-starter.war ${DEPLOYMENT_DIR}