# Guitaar

Guitaar is a JavaFX inventory management project for Java Programming, second semester of the second year. It tracks articles, stock, categories, suppliers, sectors, pricing, and receipts.

**Status: Work in progress, discontinued.** This repository preserves the original Java coursework. Development continues in [Guitaar C++](https://github.com/Ralvi07/Guitaar-CPP).

## Run on Windows

Download the Windows x64 ZIP from [Releases](https://github.com/Ralvi07/Guitaar/releases), extract the whole folder, and open **Guitaar.exe**. The package includes Java and JavaFX. Use a folder you can write to because the app saves inventory and receipts in its working directory.

The coursework demo login is **Gaster666** with password **1225**. It is a fixed classroom login.

## Build from source

Use JDK 21 and JavaFX 21 for your platform. In PowerShell, set the module path to the JavaFX SDK's lib folder and run the following from the repository root.

    $fx = 'C:\path\to\javafx-sdk-21\lib'
    $sources = (Get-ChildItem src -Recurse -Filter *.java).FullName
    javac --module-path $fx --add-modules javafx.controls -encoding UTF-8 -d out $sources
    java --module-path $fx --add-modules javafx.controls -cp out Main.Main
