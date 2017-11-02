package trainSimulation

import com.univocity.parsers.common.record.Record
import com.univocity.parsers.csv.CsvParser
import com.univocity.parsers.csv.CsvParserSettings
import com.univocity.parsers.csv.CsvWriter
import com.univocity.parsers.csv.CsvWriterSettings
import java.io.*

    fun main(args: Array<String>){
        scenario()

        external()
    }

    fun getRailNetwork(): RailNetwork{
        val railSegment0: RailSegment = RailSegment(railID = 0)
        val railSegment1: RailSegment = RailSegment(railID = 1)
        val railSegment2: RailSegment = RailSegment(railID = 2)
        val railSegment3: RailSegment = RailSegment(railID = 3)
        val railSegment4: RailSegment = RailSegment(railID = 4)
        val railSegmentList: List<RailSegment> = listOf(railSegment0,railSegment1,railSegment2,railSegment3,railSegment4)

        val railNetwork: RailNetwork = RailNetwork(railSegments = railSegmentList)

        return railNetwork
    }

    fun scenario(){

 //       val railNetwork: RailNetwork = getRailNetwork()

        val schedule0: Schedule = Schedule(railSegment = 0)
        val schedule1: Schedule = Schedule(railSegment = 1)
        val schedule2: Schedule = Schedule(railSegment = 2)
        val schedule3: Schedule = Schedule(railSegment = 3)
        val schedule4: Schedule = Schedule(railSegment = 4)

        val train0: Train = Train(trainID = 0, schedule = schedule4)
        val train1: Train = Train(trainID = 1, schedule = schedule1)
        val train2: Train = Train(trainID = 2, schedule = schedule0)
        val train3: Train = Train(trainID = 3, schedule = schedule2)
        val train4: Train = Train(trainID = 4, schedule = schedule4)
        val train5: Train = Train(trainID = 5, schedule = schedule4)
        val train6: Train = Train(trainID = 6, schedule = schedule4)
        val train7: Train = Train(trainID = 7, schedule = schedule1)
        val train8: Train = Train(trainID = 8, schedule = schedule1)
        val train9: Train = Train(trainID = 9, schedule = schedule0)
        val train10: Train = Train(trainID = 10, schedule = schedule3)
        val train11: Train = Train(trainID = 11, schedule = schedule3)

        val trainList: List<Train> = listOf(train1,train2,train3,train4,train5,train6,train7,train8,train9,train10,train11)

        val railNetwork: RailNetwork = getRailNetwork()

        railNetwork.delayFunction(trainList, railNetwork.railSegments)

        val processedTrainList: List<Train> = railNetwork.applyDelay(trainList,railNetwork.railSegments)

        for(train in trainList){
            var textDummy: String = "Train No. '${train.trainID}' is delayed: '${train.delayed}'"
            printResults(textDummy)
        }

    }

    fun printResults(text: String){
        println(text)
    }

    fun parseInputOfCSV(fileName: String): MutableList<Train> {
        var trainListCSV: MutableList<Train> = mutableListOf()

        var settings = CsvParserSettings()
        settings.format.setLineSeparator("\n")
        settings.isHeaderExtractionEnabled = true

        var csvParser = CsvParser(settings)

        var reader = FileAccess().getReader("/" + fileName)

        var allRows: MutableList<Record> = csvParser.parseAllRecords(reader)

        for (record in allRows) {
            var id_String: String = record.values[0]
            var schedule_String: String = record.values[1]

            var id_Int: Int = id_String.toInt()
            var schedule_Int: Int = schedule_String.toInt()

            var scheduleDummy: Schedule = Schedule(schedule_Int)
            trainListCSV.add(Train(trainID = id_Int, schedule = scheduleDummy))
        }
        return trainListCSV
    }

    fun external(){
        val railNetwork: RailNetwork = getRailNetwork()
        var trainListCSV: List<Train> = parseInputOfCSV(fileName = "../../resources/TrainSchedule.csv")

        railNetwork.railSegments = railNetwork.delayFunction(trainListCSV,railNetwork.railSegments)

        trainListCSV = railNetwork.applyDelay(trainListCSV,railNetwork.railSegments)

        printResultsToCSV(trainListCSV)
    }

    fun printResultsToCSV(results: List<Train>) {
        var outputFile: String = "results.csv"

        val writer = FileAccess().getWriter(outputFile)

        val csvWriter = CsvWriter(writer, CsvWriterSettings())

        // Write the record headers of this file
        val vehicleRows: MutableList<Array<Any>> = mutableListOf()
        val id = "TrainID"
        val delay = "Delayed?"
        val row: Array<Any> = arrayOf(id, delay)
        vehicleRows.add(row)

        for (train in results) {
            val id = train.trainID.toString()
            val delay = train.delayed.toString()
            val row: Array<Any> = arrayOf(id, delay)
            vehicleRows.add(row)
        }
        csvWriter.writeRowsAndClose(vehicleRows)
    }