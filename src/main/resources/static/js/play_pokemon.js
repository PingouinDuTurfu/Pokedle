function tryPokemon() {
    const pokemonToTry = $("#selectSearchInput").val();
    $.post("/play/official_try",
        {pokemonName: pokemonToTry},
        function(data, status){
        console.log(data)
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
