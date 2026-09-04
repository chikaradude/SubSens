# SubSens

**SubSens** is a simple, lightweight, client-side Fabric mod that allows Minecraft's mouse sensitivity to go negative while keeping it precise.

## Features

- Allows sensitivity slider go down to roughly `-67%`, which is basically the limit of camera movement
- Stores precise sensitivity value directly in `options.txt`
- Supports proper negative `mouseSensitivity` values
- No separate menus or config files
- Fully client-side
- No dependencies

## How it works
Minecraft normally limits mouse sensitivity to values between `0.0` → `1.0`.

**SubSens** extends the lower end of that range, allowing negative values.

## Coverting sensitivity to match cm/360

If you already have a Minecraft sensitivity value for one DPI and want to switch to another DPI while keeping the same physical movements, use:

```text
newSens = (oldDPI / newDPI)^(1/3) × (oldSens + 1/3) - 1/3
```

Example:

```text
oldSens = 0.00066961
oldDPI  = 690
newDPI  = 800
newSens = -0.01539956185845284
```

You can also use sensitivity converters that provide the correct negative `mouseSensitivity values.

## Support
**SubSens** is free and open source.
If you would like to support the project, use [Suppi by Patronite](https://suppi.pl/chikaradude).
