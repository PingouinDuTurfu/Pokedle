function tryPokemon() {
    const pokemonToTry = $("#selectSearchInput").val();
    $.post("/play/official_try",
        {pokemonName: pokemonToTry},
        function(data, status) {
            const answerTable = $("#classic-game-answer-content");
            const newLine = "" +
                "<ul class=\"answerLineContent\">" +
                    "<li class=\"answerItem\"><img src='http://www.pingouinduturfu.fr/pokedle/thumbnails-compressed/" + data["pokemon"]["linkIcon"] + "' alt='pokemon'></li>" +
                    "<li class=\"answerItem\"><span>" + data["pokemon"]["nameFr"] + "</span></li>" +
                    "<li class=\"answerItem\"><span>" + data["difference"]["color"] + "</span></li>" +
                    "<li class=\"answerItem\"><span>" + data["difference"]["shape"] + "</span></li>" +
                    "<li class=\"answerItem\"><span>" + data["difference"]["type"] + "</span></li>" +
                    "<li class=\"answerItem\"><span>" + data["difference"]["weight"] + "</span></li>" +
                    "<li class=\"answerItem\"><span>" + data["difference"]["height"] + "</span></li>" +
                    "<li class=\"answerItem\"><span>" + "hasagy" + "</span></li>" +
                "</ul>";
            answerTable.append(newLine);
    });
}

const FILTER_RESET = "----";
const FILTER_NULL = "";


function filterFunction() {
    let input, filter, contentDiv, buttonItems;
    input = document.getElementById("selectSearchInput");

    if(input.value === FILTER_NULL)
        filter = FILTER_RESET;
    else
        filter = input.value.toUpperCase();

    contentDiv = document.getElementById("classicGameSearchContent");
    buttonItems = contentDiv.getElementsByTagName("button");

    for (const item of buttonItems) {
        const textValue = item.getElementsByTagName("span").item(0).textContent;
        if(!textValue.toUpperCase().startsWith(filter) || textValue.toUpperCase() === filter)
            item.style.display = "none";
        else
            item.style.display = "flex";
    }
}

function selectPokemon(select) {
    $("#selectSearchInput").val(select.name);
    filterFunction();
}

$(document).ready(() => {
    filterFunction();
});
