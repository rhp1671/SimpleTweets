# Assignment 3 - *Simple Twitter Client (Android)*

**Overview: Build a simple Twitter client that supports viewing a Twitter timeline and composing a new tweet.**

Submitted by: **RPrasad**

Time spent: **22** hours spent in total


##User Stories:

The following user stories must be completed:

User Stories:

The following user stories must be completed:

* [X] User can sign in to Twitter using OAuth login (2 points)
* [X] User can view the tweets from their home timeline (4 points)
* [X] User should be displayed the username, name, and body for each tweet
* [X] User should be displayed the relative timestamp for each tweet "8m", "7h"
* [X] User can view more tweets as they scroll with infinite pagination
* [X] User can compose a new tweet (4 points)


* The following advanced user stories are optional:

* [X] While composing a tweet, user can see a character counter with characters remaining for tweet out of 140 (1 point)
* [X] Links in tweets are clickable and will launch the web browser (see autolink) (1 point)
* [X] User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh) (1 point)
* [] User can open the twitter app offline and see last loaded tweets
* [] Tweets are persisted into sqlite and can be displayed from the local DB (2 points)
* [] User can tap a tweet to display a "detailed" view of that tweet (2 points)
* [] User can select "reply" from detail view to respond to a tweet (1 point)
* [] Improve the user interface and theme the app to feel "twitter branded" (1 to 5 points)
* [] Stretch: User can see embedded image media within the tweet detail view (1 point)
* [] Stretch: User can watch embedded video within the tweet (1 point)
* [X] Stretch: Compose activity is replaced with a modal overlay (2 points)
* [X] Stretch: Use Parcelable instead of Serializable using the popular Parceler library. (1 point)
* [X] Stretch: Leverage RecyclerView as a replacement for the ListView and ArrayAdapter for all lists of tweets. (2 points)
* [X] Stretch: Move the "Compose" action to a FloatingActionButton instead of on the AppBar. (1 point)
* [X] Stretch: On the Twitter timeline, leverage the CoordinatorLayout to apply scrolling behavior that hides / shows the toolbar. (1 point)
* [] Stretch: Replace all icon drawables and other static image assets with vector drawables where appropriate. (1 point)
* [X] Stretch: Leverage the data binding support module to bind data into one or more activity or fragment layout templates. (1 point)
* [X] Stretch: Replace Picasso with Glide for more efficient image rendering. (1 point)
* [] Stretch: Enable your app to receive implicit intents from other apps. When a link is shared from a web browser, it should pre-fill the text and title of the web page when composing a tweet. (1 point)
* [] Stretch: When a user leaves the compose view without publishing and there is existing text, prompt to save or delete the draft. If saved, the draft should then be persisted to disk and can later be resumed from the compose view. (2 points)



## Video Walkthrough 

Here's a walkthrough of implemented user stories:

![Video Walkthrough](walkthrough.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## License

    Copyright [2017] [RPRASAD]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


