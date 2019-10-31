# Seasons: Core
<img src="https://github.com/Fabric-Seasons/seasons-core/blob/master/src/main/resources/assets/seasonscore/icon.png" width="100" height="100">
Core API for the Seasons mod suite. Includes utilities for monitoring the day, year, and season.

[![](https://jitpack.io/v/Fabric-Seasons/seasons-core.svg)](https://jitpack.io/#Fabric-Seasons/seasons-core)

## What?
  Seasons: Core is an API library which keeps track of the day, year, and season in your world. It also provides hooks for developers to manipulate and react to the date. As a user, you'll most likely only see it working in the background in other mods: it offers no content on its own aside from a command used to check the current date:

```
/season poll
```

By default, a season lasts **15** days, and there are 4 seasons per year. This means each season is exactly 5 hours of real life time. This exact value is still up for change, but it's fully configurable in the `config/seasons-core.json`file.

## Downloading as a Developer
Including `Seasons: Core` in your project is easy. Add jitpack as a repository, and point to this project in your dependencies block.
```gradle
repositories {
  [...]
  maven { url = "https://jitpack.io" }
}
```

```gradle
repositories {
  modImplementation 'com.github.Fabric-Seasons:seasons-core:1.0.0-beta'
  include 'com.github.Fabric-Seasons:seasons-core:1.0.0-beta'
}
```

*Seasons: Core* is a small library, so we highly recommend bundling it (JiJ) if you plan on depending on it.

## Downloading as a User
If a developer has decided to not include Seasons: Core in their mod, you can download a jar on the CurseForge page.

## Developers: Usage
The primary class you'll use as a developer is `SeasonManager`. You'll be able to poll for the current Date and set the day, year, and season if you want to.

```java
Date currentDate = SeasonManager.getDate(world);
```

Several printing utilities can be found in `SeasonUtils`, which give you a way to monitor and debug date changes:
```java
System.out.println(SeasonUtils.getFormattedDateUpdateMessage(date)));
```

A date change callback is also included, which is called when the time hits 24000 and the day count is incremented.
It gives you access to the previous date as well as the new, or current, date.

```java
DateIncrementCallback.EVENT.register((serverWorld, previousDate, newDate) -> {
	LOGGER.info("[Seasons: Core] [DEV ENV]: " + SeasonUtils.getFormattedDateUpdateMessage(newDate));
});
```

## License
This project is available under the MIT license.
