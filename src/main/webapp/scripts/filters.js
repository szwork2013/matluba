shopstuffsApp.filter("shorten", function(){
    return function(input){
        var length = input.length;
        var names = input.split(input.lastIndexOf("."));
        return length > 40 ? names[0].substring(0, 30) + "..." + name[1]:input;
    }
});