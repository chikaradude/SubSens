# SubSens

**SubSens** is a simple, lightweight, client-side Fabric mod that allows Minecraft's mouse sensitivity to go negative while keeping it precise.

## Features

- Allows sensitivity slider go down to roughly `-67%`, which is basically the limit of camera movement
- Stores precise sensitivity value directly in `options.txt`
- Supports proper negative `mouseSensitivity` values
- No separate menus or config files
- No dependencies

## How it works

By default, Minecraft limits mouse sensitivity to values between `0.0` and `1.0`. However, Minecraft's `0.0` is not actually 0. There is still some camera movement left.

**SubSens** extends the lower end of Minecraft's default sensitivity range, allowing you to go all the way down to the actual `0`.

## Coverting sensitivity to match cm/360

If you already have a Minecraft sensitivity for one DPI and want to switch to another DPI while keeping the same physical hand movements, use:

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

You can also use sensitivity converters that provide the correct negative `mouseSensitivity` values.

## Support
**SubSens** is free and open source.
If you would like to support the project, use [Suppi by Patronite](https://suppi.pl/chikaradude).
