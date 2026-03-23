# PEN Design - Beginner Friendly Java

This version is simple, but still follows core system design ideas.

## Principles Followed

- SRP: `Pen` handles pen behavior, `Refill` handles ink data.
- OCP: ink calculation can be changed by creating another `InkUsageStrategy`.
- DIP: `Pen` depends on `InkUsageStrategy` interface, not hardcoded math.

## Pattern Used

- Strategy Pattern:
	- `InkUsageStrategy`
	- `SimpleInkUsageStrategy`

## Classes

- `Main`
- `Pen`
- `Refill`
- `InkUsageStrategy`
- `SimpleInkUsageStrategy`
- `PenType`
- `InkColor`
- `PenState`

## Run (PowerShell)

```powershell
cd "a:\System Design\PEN Design"
mkdir out -Force
javac -d out (Get-ChildItem -Filter *.java | ForEach-Object { $_.FullName })
java -cp out Main
```
