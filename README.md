# ATH Sample

**ATH Sample** is a sample Authentication and Authorization Application with Kotlin Language and MVVM architecture.

## Overview

**ATH Sample** is a sample project for Authentication and Authorization in android Application with Shared Preferences and Room Database.

This Project use:

- MVVM Architecture 
- Koin Dependency Injection
- Room 
- JavaMail Api
- Kotlin Coroutines
- Shared Prefrences
- [Crypto](https://github.com/KazaKago/Cryptore) Library.

Also when the user Signup into the app, the password is Encrypted and saved into Database. When the user logs out and then logs in again, the password is read from the database and then decrypted for verification. It's better for **Security**.

## Screenshots
<img width="200" alt="ATH_Sample_welcome_page" src="https://user-images.githubusercontent.com/73066290/118691024-e924d680-b81d-11eb-9e91-c78b0a7e86df.png"> <img width="200" alt="ATH_Sample_signin_page" src="https://user-images.githubusercontent.com/73066290/118691048-ee822100-b81d-11eb-8e3f-41454fd19a1e.png"> <img width="200" alt="ATH_Sample_signup_page" src="https://user-images.githubusercontent.com/73066290/118691054-f04be480-b81d-11eb-9b33-251c030c7b74.png"> <img width="200" alt="ATH_Sample_main_page" src="https://user-images.githubusercontent.com/73066290/118691057-f17d1180-b81d-11eb-9f30-5d32e1f44b94.png">


### Demo
![20210518-213531](https://user-images.githubusercontent.com/73066290/118696654-c4336200-b823-11eb-9377-f4c0881daadd.gif)

## Tips

For Forgot Password section in this app, There are several things you need to keep in mind:

1. You must enter an Email that doesn't contain **2-Step Verification**.

2. You must Launch application on **Physical device**.

3. Make sure Less secure apps is **TURNED ON.**

   Check: https://www.google.com/settings/security/lesssecureapps

4. Allow each app to send email. Go to https://accounts.google.com/b/0/DisplayUnlockCaptcha and click on Continue.

5. For the last step, Enter your **Email** and **Password** in the **SendEmailApi** class in the project.

## Contribution

If you have any ideas or issues, don't hesitate to make contact via the Issues page. Every contribution is welcome. I see every issues and pull requests you made.

## Find this repository useful? ❤️

Support it by joining **[stargazers](https://github.com/AbolfaZlRezaEe/ATH-Sample/stargazers)** for this repository. ⭐
And **[follow](https://github.com/AbolfaZlRezaEe)** me for my next creations! 🤩



