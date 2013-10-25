(function ($) {
    "use strict";

    /**
     *
     * @param data
     * @returns {number}
     */
    var getMaxHits = function (data) {
        var maxHits = 0;
        $.each(data, function (tag, hits) {
            maxHits = Math.max(hits, maxHits);
        });
        return maxHits || 1;
    };

    /**
     *
     * @param event
     */
    var onMoodButtonClicked = function (event) {
        var mood = $(this).attr("data-mood") || "";
        /*TODO - deactivate the other buttons and highlight the selected one*/

        $(".buttons-container .mood-button").addClass("inactive");
        $(this).removeClass("inactive");

        $.getJSON("service/tags/" + mood.toUpperCase(), function (data) {
            var tagsContainer, maxHits, usedTags,
                newTags = $(".new-tags").removeClass("hidden");
            if (data && data.content) {
                tagsContainer = $(".tags-container").removeClass("hidden");
                usedTags = tagsContainer.find(".used-tags").html("");
                maxHits = getMaxHits(data.content);
                $.each(data.content, function (tag, hits) {
                    var fontSize = Math.round((hits / maxHits) * (35 - 8) + 8) + "px";
                    usedTags.append("<span class='tag' style='font-size: " + fontSize + "'>" + tag + "</span> ");
                });
            }
        });
    };

    /**
     *  handle the tag selecting - add the tag value to the input for a new submit
     */
    var onTagClicked = function () {
        // ad the selected tag to the new tag input
        $(".new-tags input").val($(this).html())
    };

    var onRemoveButtonClicked = function () {
        // remove all the content from the new tags input
        $(".new-tags input").val("");
    };

    var onDoneButtonClicked = function () {
        var postData = {
            "mood" : ($(".buttons-container .mood-button").not(".inactive").attr("data-mood") || "neutral").toUpperCase(),
            "tag"  : $(".new-tags input").val()
        };

        $.ajax({
            'type': "POST",
            'url': "/service/vote",
            'contentType': 'application/json',
            'data': JSON.stringify(postData),
            'dataType': 'json',
            'success': function () {
                $(".tags-container, .new-tags").addClass("hidden");
            }
        });

        $(".new-tags input").val("");
        $(".buttons-container .mood-button").removeClass("inactive");
    };

    $(document).ready(function () {
        // Add event handlers for mood buttons
        $(".buttons-container").on("click", ".mood-button", onMoodButtonClicked);
        $(".tags-container").on("click", ".tag", onTagClicked);
        $(".new-tags").on("click", ".button-remove", onRemoveButtonClicked);
        $(".new-tags").on("click", ".button-done", onDoneButtonClicked);
    });

})(jQuery);