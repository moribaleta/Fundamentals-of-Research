var dataset = [{
        Outlook: 'Sunny',
        Humidity: 'High',
        Wind: 'Weak',
        Play: false
    },
    {
        Outlook: 'Sunny',
        Humidity: 'High',
        Wind: 'Strong',
        Play: false
    },
    {
        Outlook: 'Overcast',
        Humidity: 'High',
        Wind: 'Weak',
        Play: true
    },
    {
        Outlook: 'Rain',
        Humidity: 'High',
        Wind: 'Weak',
        Play: true
    },
    {
        Outlook: 'Rain',
        Humidity: 'Normal',
        Wind: 'Weak',
        Play: true
    },
    {
        Outlook: 'Rain',
        Humidity: 'Normal',
        Wind: 'Strong',
        Play: false
    },
    {
        Outlook: 'Overcast',
        Humidity: 'Normal',
        Wind: 'Strong',
        Play: true
    },
    {
        Outlook: 'Sunny',
        Humidity: 'High',
        Wind: 'Weak',
        Play: false
    },
    {
        Outlook: 'Sunny',
        Humidity: 'Normal',
        Wind: 'Weak',
        Play: true
    },
    {
        Outlook: 'Rain',
        Humidity: 'Normal',
        Wind: 'Weak',
        Play: true
    },
    {
        Outlook: 'Sunny',
        Humidity: 'Normal',
        Wind: 'Strong',
        Play: true
    },
    {
        Outlook: 'Overcast',
        Humidity: 'High',
        Wind: 'Strong',
        Play: true
    },
    {
        Outlook: 'Overcast',
        Humidity: 'Normal',
        Wind: 'Weak',
        Play: true
    },
    {
        Outlook: 'Rain',
        Humidity: 'High',
        Wind: 'Strong',
        Play: false
    },
];
var tree = {};
var tree_temp = {};
main();

function main() {
    var object = new Node();
    console.log(object.output());
    var keyset = Object.keys(dataset[0]);
    //look for attribute
    keyset = keyset.slice(0,keyset.length-1);
    for (var set in keyset) {
        tree[keyset[set]] = [];
        for (var i = 0; i < dataset.length; i++) {
            if (tree[keyset[set]].indexOf(dataset[i][keyset[set]]) == -1) {
                tree[keyset[set]].push(dataset[i][keyset[set]]);
            }
        }
    }
    //check for purity
    for (var attribute in tree) {
        console.log('attribute: %o', tree[attribute]);
        for (var node in tree[attribute]) {

            var pure = true;
            for (var i = 0; i < dataset.length; i++) {
                console.log(dataset[i][attribute] +' :vs: '+ tree[attribute][node]);
                if (dataset[i][attribute] == tree[attribute][node]) {
                    console.log(!dataset[i].Play);
                    if (!dataset[i].Play) {
                        pure = false
                    }
                }
            }
            console.log('node: ' + node);
            tree_temp[tree[attribute][node]] = pure;
        }
    }


    console.log('tree: %o', tree);
    console.log('treep_temp: %o', tree_temp);
    var node_tree = [];
    for(var i = 0; i<tree.length;i++){
        for(var index of tree[i]){
            if(!tree_temp[tree[i][index]]){
                var node = new Node();

            }else{

            }
        }
    }

}


function Node() {
    this.children = [];
    this.value;
    this.result;
}