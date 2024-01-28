# Technical guide
*
oc new-app \
-e MYSQL_USER=ajgarcia \
-e MYSQL_PASSWORD=password \
-e MYSQL_DATABASE=products \
registry.redhat.io/rhscl/mysql-56-rhel7

oc expose service/mysql-56-rhel7

docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=root_password -e MYSQL_DATABASE =products -e MYSQL_USER=ajgarcia -e MYSQL_PASSWORD=password -p 3306:3306 -d mysql:8.3.0

      containers:
        - name: container
          image: ajgarciaparadigma/test_bme

          env:
            - name: spring.profiles.active
              value: oc

http://example-ajgarciagm-dev.apps.sandbox-m4.g2pi.p1.openshiftapps.com/products/1

docker buildx build --platform linux/amd64 -t ajgarciaparadigma/test_bme_secrets --push .
