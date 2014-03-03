/***
 * Fun��o an�nima executada no final da renderiza��o das p�ginas com
 * prop�sito de destacar dinamicamente a op��o de menu selecionada.
 * 
 * Necess�rio porque o c�digo do menu � reaproveitado pelas p�ginas.
 * 
 * Funciona percorrendo via JQuery as tags A com a classe NAV de cada
 * op��o de menu, comparando os seus endere�os com o da p�gina atual.
 * Se s�o iguais, adiciona a classe ACTIVE do bootstrap � sua tag LI.
 * 
 * @author Denys
 */
$(document).ready(function () {
    var path = window.location.pathname;
    path = path.replace(/\/$/, "");
    path = decodeURIComponent(path);

    $(".nav a").each(function () {
        var href = $(this).attr('href');
        if (path.substring(0, href.length) === href.substring(0, path.length)) {
            $(this).closest('li').addClass('active');
        }
    });
});