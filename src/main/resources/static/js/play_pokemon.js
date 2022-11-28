

function tryPokemon() {
    const pokemonToTry = $("#myInput").val();
    $.post("/play/official_try",
        {pokemonName: pokemonToTry},
        function(data, status){
        console.log(data)
    });
}


function filterFunction() {
    var input, filter, a, i;
    input = document.getElementById("myInput");
    console.log(input.value)
    if (input.value === "")
        filter = "42";
    else
        filter = input.value.toUpperCase();
    div = document.getElementById("myDropdown");
    a = div.getElementsByTagName("button");
    console.log(a)
    for (i = 0; i < a.length; i++) {
        txtValue = a[i].getElementsByTagName("span").item(0).textContent;
        if (txtValue.toUpperCase().startsWith(filter)) {
            a[i].style.display = "";
        } else {
            a[i].style.display = "none";
        }
    }
}

function selectPokemon(select) {
    const pokemonToTry = $("#myInput").val(select.name);
    filterFunction();
}





$(document).ready(() => {
    document.getElementById("myDropdown").classList.toggle("show");
    filterFunction();
});
