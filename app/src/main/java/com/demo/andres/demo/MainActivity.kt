package com.demo.andres.demo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    val extraMessage = "com.demo.andres.MESSAGE"
    var beforeOperation :String = ""
    var accumulatePartial:Double = 0.0
    var previosOperation : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btnNumber(view: View){
      val editText = findViewById<EditText?>(R.id.editText2)
      var strBtn = (view as Button).text
      if(editText?.text.toString() == "Error"){
        return
      }
      if(strBtn == "." && editText?.text?.indexOf(".") != -1){
        return
      }

      if(previosOperation ||(strBtn != "." && editText?.text?.toString() == "0")) editText?.text?.clear()
      if(editText?.text.toString() == "" && strBtn == ".") strBtn = "0."
      editText?.append(strBtn)
      previosOperation = false
    }

    fun btnEqual(view: View){
      if(findViewById<EditText?>(R.id.editText2)?.text?.toString() == "Error"){
        return
      }
      val value = findViewById<EditText?>(R.id.editText2)?.text?.toString()?.toDouble()
      val textView =  findViewById<TextView?>(R.id.textView2)
      if(textView?.text.toString() == "") return
      if(beforeOperation != "" && value!= null){
        if(operationWrapper(value) == 0){
          textView?.text = ""
          printAccumulate()
        }
      }
      accumulatePartial = 0.0
    }

    fun printAccumulate(){
      val editText = findViewById<EditText?>(R.id.editText2)
      if(accumulatePartial%1 == 0.0)
        editText?.setText(accumulatePartial.toInt().toString())
      else
        editText?.setText(accumulatePartial.toString())
    }


    private fun operationWrapper(arg1: Double): Int{
      try {
        accumulatePartial = applyOperation(accumulatePartial, arg1, beforeOperation)
        return 0
      }
      catch (e:Exception){
        findViewById<EditText?>(R.id.editText2)?.setText("Error")
        findViewById<TextView?>(R.id.textView2)?.text = ""
        return 1
      }
    }

    fun applyOperation(arg1: Double, arg2: Double, operation: String) :Double{
      when(operation){
        "+" -> return arg1 + arg2
        "-" -> return arg1 - arg2
        "x" -> return arg1 * arg2
        "%" -> {
          if(arg2 == 0.0){
            throw Exception("operation floating error: divition by 0")
          }
          else{
            return (arg1*arg2)/100
          }
        }
        "/" -> {
          if (arg2 == 0.0)
            throw Exception("operation floating error: divition by 0")
          else
            return arg1 / arg2
        }
      }
      throw Exception("unhandled operation")
    }

    fun btnOperation(view: View){
      val valueStr = findViewById<EditText?>(R.id.editText2)?.text?.toString()
      if(valueStr == "Error") return
      val value = valueStr?.toDouble()
      val strBtn = (view as Button).text?.toString()
      val textView =  findViewById<TextView>(R.id.textView2)

      if(previosOperation || value == null) return
      previosOperation = true
      if(value % 1 == 0.0)
        textView?.append(" ${value.toInt()} $strBtn")
      else
        textView?.append(" $value $strBtn")

      if(accumulatePartial != 0.0){
        if(operationWrapper(value) != 0){
          beforeOperation = ""
          return
        }
      }
      else{
        accumulatePartial = value
      }
      beforeOperation = strBtn!!
      printAccumulate()
    }

    fun clearAll(view: View){
        findViewById<EditText?>(R.id.editText2)?.setText("0")
        findViewById<TextView?>(R.id.textView2)?.text = ""
        accumulatePartial = 0.0
    }

  fun toogleSigne(view: View){
    val editText = findViewById<EditText?>(R.id.editText2)
    val valueStr = editText?.text?.toString()
    if(valueStr == "Error") return
    var value = valueStr?.toDoubleOrNull()
    if(value == null) return
    value *=-1.0
    if(value % 1.0 == 0.0)
      editText?.setText(value.toInt().toString())
    else
      editText?.setText(value.toString())
  }

  fun removeLast(view: View){
    val editText = findViewById<EditText?>(R.id.editText2)
    var valueStr = editText?.text?.toString()
    if(valueStr == "Error" || valueStr == "0") return
    var value = valueStr?.toDoubleOrNull()
    if(value == null) return
    valueStr = valueStr?.subSequence(0, valueStr.length-1) as String
    if(valueStr == "") valueStr = "0"
    editText?.setText(valueStr)
  }
}


