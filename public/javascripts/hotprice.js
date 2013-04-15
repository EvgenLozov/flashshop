/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 10.01.13
 * Time: 23:47
 * To change this template use File | Settings | File Templates.
 */
//

jQuery.get("/hotprice/settings",{},displaySettings);

function displaySettings(data){
    var allSettings = data;
    form = document.getElementById("button");
    for (i=0; i<allSettings.length; i++) {
        var a = document.createElement("a");
        a.setAttribute("id",allSettings[i].id);
        a.setAttribute("onClick","displayCategorySettings(this.id);  return false");
        a.innerText += allSettings[i].name;

        form.appendChild(a);
        form.innerHTML += "&nbsp;"
    }
}

function displayCategorySettings(settingsId){
    settingId = settingsId;
    var deleteForm = document.getElementById("categories");
    deleteForm.innerHTML = "";
    jQuery.get("/flashshop/categories",{},displayCategories);

    function displayCategories(data){
         var categories = data;

        jQuery.get("/hotprice/selectedcategories",{id:settingsId},selectedCategories);
        function selectedCategories (data){

            var selectedCategories = data;

            var categoryMap = {};

            for(var i=0; i<data.selectedCategory.length; i++){
                categoryMap[data.selectedCategory[i].categoryId] = data.selectedCategory[i];
            }

            var form = document.getElementById("categories");
            for (i=0; i<categories.length; i++) {
                var input = document.createElement("input");
                input.setAttribute("type","checkbox");
                input.setAttribute("id",categories[i].categoryId);
                input.setAttribute("name","category");
                input.setAttribute("value",categories[i].categoryName);

                var id = categories[i].categoryId;
                if (categoryMap[id] != undefined)
                    input.setAttribute("checked","checked");

                form.appendChild(input);
                form.innerHTML += categories[i].categoryName;
                form.appendChild(document.createElement("br"));
            }

            var button = document.createElement("input");
            button.setAttribute("type","button");
            button.setAttribute("value","Apply");
            button.setAttribute("onClick","setNewSelectedCategories("+settingId+")");
            form.appendChild(button);
        }
        //settingId = null;
    }

}
function setNewSelectedCategories(){
    var checkedCategories = [];
    var count = 0;
    var form = document.getElementById("categories");
    var inputs = form.getElementsByTagName("input");
    for (var i=0;i<inputs.length-1;i++){
        if (inputs[i].checked) {
        var category = {};
        category.id = inputs[i].getAttribute("id");
        category.name = inputs[i].value;
        checkedCategories[count] = category;
        count = count + 1;
        }
    }

    var newSettings = {};
    newSettings["checkedCategories"] = checkedCategories;
    newSettings["settingId"] = settingId;

    jQuery.ajax({
        url:"/hotprice/categories",
        type:"POST",
        data:JSON.stringify(newSettings),
        contentType:"application/json"
    })
}

function start(){
    jQuery.get("/hotprice/start",{id:settingId},
    message
        );
}

function stop(){
    jQuery.get("/hotprice/stop",{id:settingId},
    message
        );
}

function setInterval(){
    jQuery.get("/hotprice/setinterval?period="+document.getElementById("period").value,{id:settingId},
        message
    );
}

function getPrice(){
    jQuery.get("/hotprice/pricelist"
    );
}

function message(data){
    alert(data);
}