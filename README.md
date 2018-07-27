# fbSpam

Well I started with the idea that I will be able to code up using the facebook api and generate some chaos with it. The initial idea I had while starting was to:
* Reply to all the users who wished me on my birthday.
    * I could not do this because, Facebook App is very restrictive about whose data it has access to. So I cannot fetch the post of some user from my wall, if that user has not allowed my app to fetch his posts/feed.
    * Silver lining here was that I was able to fetch the posts made by the user on a given day.
* Automatically wish a user on his Birthday. 
    * I could not do this because of the same reason as stated above.
* Fetch all the books.
    * I was able to fetch all the names of the books I have liked.
* Fetch all the pages I have liked and fetch details about the page and bucket my likes into various categories. This was not possible because my App was not published. May be I will do that sometime soon and then see try to if I can fetch the page data/metadata.
* Ability to retrieve the user token automatically. Honestly, no clue here. I think involves me calling the FB api after a user has agreed for consuming my App. This will return some callback. But callbacks scare me today and I zero clue. So will defer this for now.

What I learnt about Java.
* All hail Log4J - I just loved this logger class, I used a ton of logging on Python using the standard `logging` module. The experience was as seamless as an inbuilt function.
* Maven you wonderful wonderful boy - The folder structure appealed to my O.C.D. The dependency management was smooth. Would have loved it more if it used `pom.yaml` or `pom.json` instead of `pom.xml`.
* Calendar/Date classes and the ever confusing date format strings.

What I wanted to do apart from what was mentioned above.
* Some sort concurrency. Threads, Threads Pools etc. Did not find some way I could code that up.

Status:
---
Open to fixing bugs on this. Planning to explore the [goodreads api](https://www.goodreads.com/api). It looked a little less     
