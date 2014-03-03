/***
 * Função anônima executada no final da renderização das páginas com
 * propósito de destacar dinamicamente a opção de menu selecionada.
 * 
 * Necessário porque o código do menu é reaproveitado pelas páginas.
 * 
 * Funciona percorrendo via JQuery as tags A com a classe NAV de cada
 * opção de menu, comparando os seus endereços com o da página atual.
 * Se são iguais, adiciona a classe ACTIVE do bootstrap à sua tag LI.
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