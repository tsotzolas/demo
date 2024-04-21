(function ($, undefined) {

    $(document).ready(function () {
        // createDemos();
        // initTheme();
        // changeTheme(e);

        $("#themeSelector").dateRangeSlider(
            {wheelMode: "zoom"},
            {
                formatter: function (val) {
                    var days = val.getDate(),
                        month = val.getMonth() + 1,
                        year = val.getFullYear();
                    return days + "/" + month + "/" + year;
                },

                bounds: {
                    min: new Date(2019, 0, 1),
                    max: new Date(2020, 11, 31)
                },
                defaultValues: {
                    min: new Date(2019, 3, 1),
                    max: new Date(2020, 8, 31)
                }
            }
        );


        $("#themeSelector").bind("valuesChanging", function (e, data) {
            console.log("min:" + toJSONLocal(data.values.min) + " max:" + toJSONLocal(data.values.max));
            var returnValue = "min:" + toJSONLocal(data.values.min) + " max:" + toJSONLocal(data.values.max);
            document.getElementById('hiddenValue').value = returnValue;
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


    });

})
(jQuery);