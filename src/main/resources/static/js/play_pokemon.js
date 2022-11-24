

function tryPokemon() {
    const pokemonToTry = $("#select_pokemon").val();
    $.post("/play/official_try",
        {pokemonName: pokemonToTry},
        function(data, status){
        alert("Data: " + data + "\nStatus: " + status);
    });
}




function filterFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myInput");
    if (input.value === "")
        filter = "42";
    else
        filter = input.value.toUpperCase();
    div = document.getElementById("myDropdown");
    a = div.getElementsByTagName("a");
    for (i = 0; i < a.length; i++) {
        txtValue = a[i].getElementsByTagName("span").item(0).textContent;
        if (txtValue.toUpperCase().startsWith(filter)) {
            a[i].style.display = "";
        } else {
            a[i].style.display = "none";
        }
    }
}

$(document).ready(() => {
    document.getElementById("myDropdown").classList.toggle("show");
    filterFunction();
});
