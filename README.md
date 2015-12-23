#Artifactory badge
Badges, badges, everywhere badges. Except for *Artifactory*. Until now :-)

This service can be used to generate an *Artifactory* badge for a project. Since there is no obvious way to navigate to a project on *Artifactory*, the badge is not interactive. All you will get is the version number of the latest deployed artifact.

Example badge:
![example-badge](example-badge.png)

##Configuration
This is a Spring boot application and can hence be configured in several ways. Use application.yml, or use environment variables.

###Examples
**application.yml**
```yml
artifactory:
  host: <artifactory_hostname>
  port: 80
  user:
    name: <artifactory_user>
    pw: <user_pw>
```

**environment variables**
```
ARTIFACTORY_HOST=<artifactory_hostname>
ARTIFACTORY_PORT=80
ARTIFACTORY_USER_NAME=<artifactory_user>
ARTIFACTORY_USER_PW=<user_pw>
```