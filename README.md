# ModFX
Modular framework for JavaFX applications.

## Key Features

* Plugins support.
* Event service for communication between different components.
* Task service for running background tasks.
* Built-in configuration manager.
* Internal database with simple ORM engine.

## How To Build 

Make sure you have [Gradle](http://gradle.org/) installed. Then checkout the project and execute

    cd modfx
    gradle build
    
You can find built package (ZIP) in the `core/build/distributions/` folder.

## Custom Plugins

As for now, there is only one way to add own plugin. New plugin should be created as subproject and included to the main project in `settings.gradle`. It is also required to add a new plugin to `core` module as `plugin` dependency (in this case it will be added to the final package automatically) and add `core` module as `provided` dependency to the new plugin build. Any plugin-specific dependency that needs to be packaged with plugin JAR should be added to the plugin build as `embedded` dependency. 

## Licenses Information
Copyright (c) 2016 Igor Volynets. All rights reserved.
The product is licensed under MIT License

**Database Engine**

H2 Database Engine
This software contains unmodified binary redistributions of H2 database engine (http://www.h2database.com/), which is dual licensed and available under the MPL 2.0 (Mozilla Public License) or under the EPL 1.0 (Eclipse Public License). An original copy of the license agreement can be found at: http://www.h2database.com/html/license.htm

**Icon Set**

Fugue Icons(c) 2013 Yusuke Kamiyamane. All rights reserved.http://p.yusukekamiyamane.com/
These icons are licensed under a Creative Commons Attribution 3.0 License http://creativecommons.org/licenses/by/3.0/
Changes: notification-counter* icons were resized, icon names were changed, application.png has been converted to ICO format.
