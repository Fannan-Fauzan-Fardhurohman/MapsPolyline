package com.fanxan.mapspolyline

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Sources {
    val json = """
        {
            "status": true,
            "message": "Success",
            "data": {
                "route": [
                    {
                        "latitude": -6.865467,
                        "longitude": 107.457351
                    },
                    {
                        "latitude": -6.86541,
                        "longitude": 107.45745
                    },
                    {
                        "latitude": -6.8653,
                        "longitude": 107.45772
                    },
                    {
                        "latitude": -6.86521,
                        "longitude": 107.45806
                    },
                    {
                        "latitude": -6.86499,
                        "longitude": 107.45891
                    },
                    {
                        "latitude": -6.86497,
                        "longitude": 107.45902
                    },
                    {
                        "latitude": -6.86492,
                        "longitude": 107.45921
                    },
                    {
                        "latitude": -6.86485,
                        "longitude": 107.45955
                    },
                    {
                        "latitude": -6.86483,
                        "longitude": 107.45963
                    },
                    {
                        "latitude": -6.86482,
                        "longitude": 107.45968
                    },
                    {
                        "latitude": -6.8647,
                        "longitude": 107.46031
                    },
                    {
                        "latitude": -6.86465,
                        "longitude": 107.4606
                    },
                    {
                        "latitude": -6.86459,
                        "longitude": 107.46093
                    },
                    {
                        "latitude": -6.86454,
                        "longitude": 107.46143
                    },
                    {
                        "latitude": -6.86452,
                        "longitude": 107.46188
                    },
                    {
                        "latitude": -6.86453,
                        "longitude": 107.4622
                    },
                    {
                        "latitude": -6.86464,
                        "longitude": 107.46221
                    },
                    {
                        "latitude": -6.86462,
                        "longitude": 107.46188
                    },
                    {
                        "latitude": -6.86464,
                        "longitude": 107.46146
                    },
                    {
                        "latitude": -6.86469,
                        "longitude": 107.46103
                    },
                    {
                        "latitude": -6.86473,
                        "longitude": 107.46077
                    },
                    {
                        "latitude": -6.86481,
                        "longitude": 107.46032
                    },
                    {
                        "latitude": -6.86492,
                        "longitude": 107.45974
                    },
                    {
                        "latitude": -6.86493,
                        "longitude": 107.45967
                    },
                    {
                        "latitude": -6.86494,
                        "longitude": 107.45958
                    },
                    {
                        "latitude": -6.86502,
                        "longitude": 107.45923
                    },
                    {
                        "latitude": -6.86506,
                        "longitude": 107.45905
                    },
                    {
                        "latitude": -6.86508,
                        "longitude": 107.45893
                    },
                    {
                        "latitude": -6.86529,
                        "longitude": 107.45808
                    },
                    {
                        "latitude": -6.86535,
                        "longitude": 107.45782
                    },
                    {
                        "latitude": -6.86543,
                        "longitude": 107.45761
                    },
                    {
                        "latitude": -6.86552,
                        "longitude": 107.45743
                    },
                    {
                        "latitude": -6.86565,
                        "longitude": 107.45723
                    },
                    {
                        "latitude": -6.86608,
                        "longitude": 107.45666
                    },
                    {
                        "latitude": -6.86647,
                        "longitude": 107.45618
                    },
                    {
                        "latitude": -6.86678,
                        "longitude": 107.4558
                    },
                    {
                        "latitude": -6.86681,
                        "longitude": 107.45576
                    },
                    {
                        "latitude": -6.86698,
                        "longitude": 107.45555
                    },
                    {
                        "latitude": -6.86702,
                        "longitude": 107.4555
                    },
                    {
                        "latitude": -6.86762,
                        "longitude": 107.45479
                    },
                    {
                        "latitude": -6.86776,
                        "longitude": 107.45481
                    },
                    {
                        "latitude": -6.86791,
                        "longitude": 107.45478
                    },
                    {
                        "latitude": -6.868,
                        "longitude": 107.45472
                    },
                    {
                        "latitude": -6.86806,
                        "longitude": 107.45463
                    },
                    {
                        "latitude": -6.86809,
                        "longitude": 107.4545
                    },
                    {
                        "latitude": -6.86805,
                        "longitude": 107.45437
                    },
                    {
                        "latitude": -6.86798,
                        "longitude": 107.4543
                    },
                    {
                        "latitude": -6.86791,
                        "longitude": 107.45425
                    },
                    {
                        "latitude": -6.86782,
                        "longitude": 107.45422
                    },
                    {
                        "latitude": -6.86773,
                        "longitude": 107.45422
                    },
                    {
                        "latitude": -6.86765,
                        "longitude": 107.45424
                    },
                    {
                        "latitude": -6.86757,
                        "longitude": 107.45428
                    },
                    {
                        "latitude": -6.86754,
                        "longitude": 107.45412
                    },
                    {
                        "latitude": -6.86752,
                        "longitude": 107.45397
                    },
                    {
                        "latitude": -6.86751,
                        "longitude": 107.45382
                    },
                    {
                        "latitude": -6.86751,
                        "longitude": 107.45334
                    },
                    {
                        "latitude": -6.86762,
                        "longitude": 107.45327
                    },
                    {
                        "latitude": -6.86774,
                        "longitude": 107.45317
                    },
                    {
                        "latitude": -6.86771,
                        "longitude": 107.45309
                    },
                    {
                        "latitude": -6.8677,
                        "longitude": 107.45299
                    },
                    {
                        "latitude": -6.86771,
                        "longitude": 107.45287
                    },
                    {
                        "latitude": -6.8677,
                        "longitude": 107.4528
                    },
                    {
                        "latitude": -6.86756,
                        "longitude": 107.45247
                    }
                ]
            }
        }
    """.trimIndent()

    fun getResultRoutes():ResultRoutes{
        val gson = Gson()
        return gson.fromJson(json, object :TypeToken<ResultRoutes>(){

        }.type)
    }
}