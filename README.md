# watson-campaign-automation-spring

<div align="center">
  
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ka4ok85/watson-campaign-automation-spring/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ka4ok85/watson-campaign-automation-spring)
[![Build Status](https://travis-ci.org/ka4ok85/watson-campaign-automation-spring.svg?branch=master)](https://travis-ci.org/ka4ok85/watson-campaign-automation-spring)
[![HitCount](http://hits.dwyl.io/ka4ok85/watson-campaign-automation-spring.svg)](http://hits.dwyl.io/ka4ok85/watson-campaign-automation-spring)

</div>

# Installation

#### Gradle
```groovy
compile group: 'com.github.ka4ok85', name: 'watson-campaign-automation-spring', version: '0.0.3'
```

#### Maven
```xml
<dependency>
    <groupId>com.github.ka4ok85</groupId>
    <artifactId>watson-campaign-automation-spring</artifactId>
    <version>0.0.3</version>
</dependency>
```

# Authentication
Use oAuth authentication mechanism. Log into WCA and request your *ClientId*, *ClientSecret* and *RefreshToken*. All three values are required for accessing WCA API.

#### Getting *ClientId* and *ClientSecret*
1. Log into WCA.
2. Go to Menu *Settings* -> *Organization Settings*.
3. Click on *Application Account Access*.
4. Click on *Add Application* button and follow instructions.

#### Getting *RefreshToken*
1. Log into WCA.
2. Go to Menu *Settings* -> *Organization Settings*.
3. Click on *Application Account Access*.
4. Click on *Add Account Access* button and follow instructions. Choose Application you have added when requested *ClientId* and *ClientSecret*.


Library does **NOT** support password authentication mechanism due to security concerns.
