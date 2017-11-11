var dataset = [{
    "abdominal pain": "sometimes",
    "weight loss": "yes",
    "poor growth": "yes",
    "diarrhea": "never",
    "extreme weakness": "sometimes",
    "nausea vomiting": "sometimes",
    "anemia": "no",
    "Mood Swings depression": "never",
    "constipation": "sometimes",
    "Eczema": "yes",
    "Joint Pain": "never",
    "Mouth Ulcers": "often",
    "Muscle Cramps": "always",
    "Easy Bruising": "always",
    "result": "FALSE"
}, {
    "abdominal pain": "sometimes",
    "weight loss": "no",
    "poor growth": "no",
    "diarrhea": "always",
    "extreme weakness": "sometimes",
    "nausea vomiting": "always",
    "anemia": "no",
    "Mood Swings depression": "sometimes",
    "constipation": "never",
    "Eczema": "yes",
    "Joint Pain": "sometimes",
    "Mouth Ulcers": "always",
    "Muscle Cramps": "never",
    "Easy Bruising": "often",
    "result": "FALSE"
}, {
    "abdominal pain": "often",
    "weight loss": "yes",
    "poor growth": "no",
    "diarrhea": "often",
    "extreme weakness": "never",
    "nausea vomiting": "sometimes",
    "anemia": "no",
    "Mood Swings depression": "always",
    "constipation": "often",
    "Eczema": "yes",
    "Joint Pain": "always",
    "Mouth Ulcers": "sometimes",
    "Muscle Cramps": "never",
    "Easy Bruising": "often",
    "result": "TRUE"
}, {
    "abdominal pain": "never",
    "weight loss": "yes",
    "poor growth": "no",
    "diarrhea": "often",
    "extreme weakness": "always",
    "nausea vomiting": "always",
    "anemia": "yes",
    "Mood Swings depression": "never",
    "constipation": "always",
    "Eczema": "no",
    "Joint Pain": "never",
    "Mouth Ulcers": "sometimes",
    "Muscle Cramps": "often",
    "Easy Bruising": "never",
    "result": "TRUE"
}, {
    "abdominal pain": "always",
    "weight loss": "yes",
    "poor growth": "yes",
    "diarrhea": "always",
    "extreme weakness": "sometimes",
    "nausea vomiting": "never",
    "anemia": "yes",
    "Mood Swings depression": "often",
    "constipation": "never",
    "Eczema": "no",
    "Joint Pain": "always",
    "Mouth Ulcers": "sometimes",
    "Muscle Cramps": "often",
    "Easy Bruising": "never",
    "result": "TRUE"
}, {
    "abdominal pain": "never",
    "weight loss": "no",
    "poor growth": "yes",
    "diarrhea": "never",
    "extreme weakness": "always",
    "nausea vomiting": "sometimes",
    "anemia": "yes",
    "Mood Swings depression": "often",
    "constipation": "always",
    "Eczema": "yes",
    "Joint Pain": "often",
    "Mouth Ulcers": "always",
    "Muscle Cramps": "never",
    "Easy Bruising": "sometimes",
    "result": "FALSE"
}, {
    "abdominal pain": "sometimes",
    "weight loss": "no",
    "poor growth": "no",
    "diarrhea": "never",
    "extreme weakness": "sometimes",
    "nausea vomiting": "sometimes",
    "anemia": "no",
    "Mood Swings depression": "sometimes",
    "constipation": "often",
    "Eczema": "no",
    "Joint Pain": "sometimes",
    "Mouth Ulcers": "sometimes",
    "Muscle Cramps": "sometimes",
    "Easy Bruising": "sometimes",
    "result": "FALSE"
}, {
    "abdominal pain": "never",
    "weight loss": "no",
    "poor growth": "no",
    "diarrhea": "sometimes",
    "extreme weakness": "always",
    "nausea vomiting": "always",
    "anemia": "yes",
    "Mood Swings depression": "never",
    "constipation": "sometimes",
    "Eczema": "yes",
    "Joint Pain": "sometimes",
    "Mouth Ulcers": "always",
    "Muscle Cramps": "always",
    "Easy Bruising": "always",
    "result": "FALSE"
}, {
    "abdominal pain": "sometimes",
    "weight loss": "yes",
    "poor growth": "yes",
    "diarrhea": "sometimes",
    "extreme weakness": "always",
    "nausea vomiting": "always",
    "anemia": "yes",
    "Mood Swings depression": "always",
    "constipation": "sometimes",
    "Eczema": "no",
    "Joint Pain": "always",
    "Mouth Ulcers": "sometimes",
    "Muscle Cramps": "often",
    "Easy Bruising": "often",
    "result": "TRUE"
}, {
    "abdominal pain": "never",
    "weight loss": "yes",
    "poor growth": "no",
    "diarrhea": "always",
    "extreme weakness": "never",
    "nausea vomiting": "always",
    "anemia": "no",
    "Mood Swings depression": "sometimes",
    "constipation": "sometimes",
    "Eczema": "yes",
    "Joint Pain": "often",
    "Mouth Ulcers": "always",
    "Muscle Cramps": "sometimes",
    "Easy Bruising": "sometimes",
    "result": "TRUE"
}];

var sample_test = {
    'abdominal pain': 'sometimes',
    'weight loss': 'yes',
    'poor growth': 'yes',
    'diarrhea': 'never',
    'extreme weakness': 'sometimes',
    'nausea vomiting': 'sometimes',
    'anemia': 'no',
    'Mood Swings depression': 'sometimes',
    'constipation': 'sometimes',
    'Eczema': 'yes',
    'Joint Pain': 'never',
    'Mouth Ulcers': 'often',
    'Muscle Cramps': 'always',
    'Easy Bruising': 'always'
};

var classifier = {};
var answer_sheet = {};

function main() {
    var keyset = Object.keys(dataset[0]);
    //look for attribute
    var result = keyset[keyset.length - 1];
    //console.log(result);
    keyset = keyset.slice(0, keyset.length - 1);
    for (var set in keyset) {
        classifier[keyset[set]] = [];
        for (var i = 0; i < dataset.length; i++) {
            if (classifier[keyset[set]].indexOf(dataset[i][keyset[set]]) == -1) {
                classifier[keyset[set]].push(dataset[i][keyset[set]]);
            }
        }
    }

    console.log(classifier);
    var temp = classifier;
    var temp_sheet = temp;
    for (var key in temp) {
        for (var subkey in temp[key]) {
            var index_diff = 0;
            var true_ = 0;
            var false_ = 0;
            //console.log('subkey: ' + classifier[key][subkey]);
            for (var i = 0; i < dataset.length; i++) {
                //console.log('dataset: '+dataset[i][key]);
                if (dataset[i][key] == temp[key][subkey]) {
                    console.log('data: ' + dataset[i][key] + ": " + dataset[i]['result']);
                    if (dataset[i][result]) {
                        index_diff++;
                        true_++;
                    } else {
                        index_diff--;
                        false_++;
                    }
                }
            }
            console.log('subkey: ' + classifier[key][subkey] + " res: " + index_diff);
            if (true_ > false_) {
                temp_sheet[key][subkey] = {
                    title: temp_sheet[key][subkey],
                    value: true
                }
            } else {
                temp_sheet[key][subkey] = {
                    title: temp_sheet[key][subkey],
                    value: false
                }
            }
            //console.log('classifier: ' + temp[key][subkey].title + ' = ' + temp_sheet[key][subkey].value);
        }
    }
    answer_sheet = temp_sheet;
    console.log('classifier %o', classifier);
    console.log('answer_sheet %o', answer_sheet);

    console.log('he will play: ' + classify());
    //var message = classify();
    /*   var ret = {
          'classifier': classifier,
          'answer_cheet': answer_sheet,
          'action': message
      } */
    //return ret;
}

function classify() {
    var result = 0;

    for (var key in sample_test) {
        for (var subkey in classifier[key]) {
            if (classifier[key][subkey].title == sample_test[key]) {
                var action = classifier[key][subkey].value;
                if (action) {
                    result++;
                } else {
                    result--;
                }
            }
        }
    }
    if (result >= 0) {
        return true;
    } else {
        return false;
    }
}

main();