
var serviceA = {
    getA: function (x) {

    }
};
var serviceB = {
    getB: function (x) {

    }
};
var serviceC = {
    getC: function (x) {

    }
};
var serviceD = {
    getD: function (x) {

    }
};
var doSomething = function(f){

}
var model = {
    data : {
        id : 3
    }
};





























serviceA.getA(model.data.id, function (respA) {
    serviceB.getB(respA.data.id, function (respB) {
        serviceC.getC(respB.data.id, function (respC) {
            serviceD.getD(respC.data.id, function (respD) {
                doSomething(respD);
            });
        });
    });
});





























