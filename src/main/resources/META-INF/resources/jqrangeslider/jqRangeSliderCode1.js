/**
 *
 * @param mindate  H min τιμή την οποία θα έχει το range του slider
 * @param maxdate  H max τιμή που θα έχει το range του slider.
 * @param slidername Το όνομα του slider.
 * @param sliderValueId To id του hidden πεδίου που θα πάει και θα βάλει την τιμή του slider.
 * @param mindefaultdate To min default date που θα έχει επιλεγμένο το slider.
 * @param maxdefaultdate To max default date που θα έχει επιλεγμένο το slider.
 */
function rangeSlider(mindate,maxdate,slidername,sliderValueId,mindefaultdate,maxdefaultdate){
    var minDate = new Date(mindate);
    var maxDate = new Date(maxdate);


    var minDateDefault;
    var maxDateDefault;
    console.log(mindefaultdate == "null");
    if(mindefaultdate == "null") {
        //Απο τις τιμες που θα δωσει ο χρηστης πηγαινουμε και βαζουμε σαν default επιλεγμενες τιμες 2 μηνες μετα το minDate και 2 μηνες πριν το maxDate.
        minDateDefault = new Date(minDate.getFullYear(), minDate.getMonth() + 2, minDate.getDay());
    }else{
        minDateDefault = new Date(mindefaultdate);
    }

    if(maxdefaultdate == "null"){
        maxDateDefault = new Date(maxDate.getFullYear(),maxDate.getMonth()-2,maxDate.getDay())
    }else{
        maxDateDefault = new Date(maxdefaultdate);
    }



    slidername = slidername.replace(":","\\:")

        $("#"+slidername).dateRangeSlider(
            {wheelMode: "zoom"},
            {
                formatter: function (val) {
                    var days = val.getDate(),
                        month = val.getMonth() + 1,
                        year = val.getFullYear();
                    return days + "/" + month + "/" + year;
                },
                bounds: {
                    min: minDate,
                    max: maxDate
                },
                defaultValues: {
                    min: minDateDefault,
                    max: maxDateDefault
                }
            }
        );


        $("#"+slidername).bind("valuesChanging", function (e, data) {

            var returnValue = {min:toJSONLocal(data.values.min),max:toJSONLocal(data.values.max) };
            var returnValueJson = JSON.stringify(returnValue);
            console.log(returnValueJson);
            document.getElementById(sliderValueId).value = returnValueJson;
        });

        /**
         * Κανει format την ημερομηνια
         * @param date
         * @returns {string}
         */
        function toJSONLocal(date) {
            var local = new Date(date);
            local.setMinutes(date.getMinutes() - date.getTimezoneOffset());
            return local.toJSON().slice(0, 10);
        }
    }

