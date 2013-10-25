(function ($) {
    "use strict";
    var getRandomInt = function (min, max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    }

    var getTags = function () {
        var tags = "animals   architecture   art   asia   australia   autumn   baby   band   barcelona   beach   berlin   bike   bird   birds   birthday   black   blackandwhite   blue   bw   california   canada   canon   car   cat   chicago   china   christmas   church   city   clouds   color   concert   dance   day   de   dog   england   europe   fall   family   fashion   festival   film   florida   flower   flowers   food   football   france   friends   fun   garden   geotagged   germany   girl   graffiti   green   halloween   hawaii   holiday   house   india   instagramapp   iphone   iphoneography   island   italia   italy   japan   kids   la   lake   landscape   light   live   london   love   macro   me   mexico   model   museum   music   nature   new   newyork   newyorkcity   night   nikon   nyc   ocean   old   paris   park   party   people   photo   photography   photos   portrait   raw   red   river   rock   san   sanfrancisco   scotland   sea   seattle   show   sky   snow   spain   spring   square   squareformat   street   summer   sun   sunset   taiwan   texas   thailand   tokyo   travel   tree   trees   trip   uk   unitedstates   urban   usa   vacation   vintage   washington   water   wedding   white   winter   woman   yellow   zoo";
        tags = tags.split("   ");
        var tagsJSON = {};
        for(var i=0; i < tags.length; i++) {
            tagsJSON[tags[i]] = getRandomInt(10, 35);
        }
        return tagsJSON;
    }

    var onMoodButtonClicked = function (event, a, b, c) {
        var mood = $(this).attr("data-mood");
        // TODO make a AJAX request to obtain the tags for the selected mood
        var tags = getTags();
        var tagsContainer = $("tags-container");
        
        for(tag in tags§ar i=0; i < tags.length; i++) {
            tagsContainer.insert("<span class='tag'>" + tag[i]. + "</span>")
        }


    };

    $(document).ready(function () {
        // Add event handlers for mood buttons
        $(".buttons-container").on("click", ".mood-button", onMoodButtonClicked);
    });

})(jQuery);