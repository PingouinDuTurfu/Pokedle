

function tryPokemon() {
    const pokemonToTry = $("#select_pokemon").val();
    $.post("/play/official_try",
        {pokemonName: pokemonToTry},
        function(data, status){
        alert("Data: " + data + "\nStatus: " + status);
    });
}