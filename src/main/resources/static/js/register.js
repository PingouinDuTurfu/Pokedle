const FILTER_RESET = "----";
const FILTER_NULL = "";
let toggle_display = false;

function display() {
    toggle_display = !toggle_display;
    let contentDiv = document.getElementById("classicGameSearchContent");
    let buttonItems = contentDiv.getElementsByTagName("button");

    if(toggle_display)
        if($("#avatar").val() !== FILTER_NULL)
            filter("classicGameSearchContent");
        else
            for (const item of buttonItems)
                item.style.display = "flex";
    else
        for (const item of buttonItems)
            item.style.display = "none";
}

function selectPokemon(select) {
    $("#avatar").val(select.name);
    filter("classicGameSearchContent");
}

function filter(selectId) {
    let input, filter, contentDiv, buttonItems;
    input = $("#avatar");
    if(input.val() === FILTER_NULL)
        filter = FILTER_RESET;
    else
    filter = input.val().toUpperCase();

    contentDiv = document.getElementById(selectId);
    buttonItems = contentDiv.getElementsByTagName("button");

    for (const item of buttonItems) {
        const textValue = item.getElementsByTagName("span").item(0).textContent;
        if(!textValue.toUpperCase().startsWith(filter) || textValue.toUpperCase() === filter)
            item.style.display = "none";
        else
            item.style.display = "flex";
    }
}