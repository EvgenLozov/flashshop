/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 16.01.13
 * Time: 22:26
 * To change this template use File | Settings | File Templates.
 */
jQuery.get("/flashshop/categories",{},showCategories);

function showCategories(data){
    var categories = data;

    jQuery.get("/actualization/categories",{},getCurrentMargins);
    function getCurrentMargins(data){
        var actalizatorsettings = data;

        var categoryMap = {};

        for(var i=0; i<data.categories.length; i++){
            categoryMap[data.categories[i].categoryId] = data.categories[i];
        }

        var form = document.getElementById("changemargin");
        for (var i=0; i<categories.length; i++) {
            var input = document.createElement("input");
            input.setAttribute("type","text");
            input.setAttribute("name",categories[i].categoryName);
            input.setAttribute("categoryid",categories[i].categoryId);

            var id = categories[i].categoryId;

            if (categoryMap[id] == undefined){
                input.setAttribute("value",10);
            }else
                input.setAttribute("value",categoryMap[id].margin);
            input.setAttribute("size","1");
            form.appendChild(input);
            form.innerHTML += categories[i].categoryName;
            form.appendChild(document.createElement("br"));
        }

        var button = document.createElement("input");
        button.setAttribute("type","button");
        button.setAttribute("value","Apply");
        button.setAttribute("onClick","setNewMargin()");
        form.appendChild(button);
    }
}

function setNewMargin(){
    var margins = [];
    var form = document.getElementById("changemargin");
    var inputs = form.getElementsByTagName("input");
    for (var i=0;i<inputs.length-1;i++){
        var category = {};
        category.id = inputs[i].getAttribute("categoryid");
        category.name = inputs[i].name;
        category.margin = inputs[i].value;

        margins[i] = category;
    }

    jQuery.ajax({
        url:"/actualization/setmargin",
        type:"POST",
        data:JSON.stringify(margins),
        contentType:"application/json"
    })
}

function start(){
    jQuery.get("/actualization/start",{},
        message
    );
}

function message(data){
    alert(data);

}

function stop(){
    jQuery.get("/actualization/stop",{},
        message
    );
}

function setInterval(){
    jQuery.get("/actualization/setinterval?period="+document.getElementById("period").value,{},
        message
    );
}

