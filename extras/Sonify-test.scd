/*
Fichier de test Sonify_Server
*/

// initialiser un serveur
~server = Sonify_Server();

// libérer le serveur
~server.free;

// soumettre une requête
~server.job(nil, {"DONE".postln});