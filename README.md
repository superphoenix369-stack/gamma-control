# Gamma Bar

A client-side Fabric mod for Minecraft **26.3** that adds an extended **Gamma** slider
(-100% to 1500%) to Video Settings. 0% is vanilla's "Moody", 100% is vanilla's "Bright",
higher values give fullbright-style visibility, and negative values push dark areas
toward pitch black.

- Client only, no server install needed
- Value is saved in `config/gammabar.properties` and re-applied on launch

## Requirements
- Minecraft 26.3
- Fabric Loader 0.19.5+
- Fabric API 0.160.5+26.3
- Java 25

## Building
This repo does not include the Gradle wrapper. Get it once by generating a project at
https://fabricmc.net/develop (Minecraft 26.3) and copying its `gradlew`, `gradlew.bat`
and `gradle/` folder into this repo's root. Then:

```
./gradlew build      # jar in build/libs/
./gradlew runClient  # test in a dev client
```

Check `gradle.properties` against the generator's versions if the build complains.

## Troubleshooting
If a mixin fails to apply, run `./gradlew genSources` and check
`VideoSettingsScreen` (method `addOptions`, field `list`) and `OptionInstance`
(field `value`) for changes in 26.3.

## License
MIT
