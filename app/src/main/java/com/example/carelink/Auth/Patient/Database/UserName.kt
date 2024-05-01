package com.example.carelink.Auth.Patient.Database

data class UserName(val name: String? = null)

data class UserList(var name: String? = null, var dob:String? = null, var gender:String? = null, var phone:String? = null, var reason:String? = null,var doctor:String? = null, var id:String? = null, var uid:String?=null)

data class DocAcc(var name: String?= null, var email: String?= null, var pass:String?=null, var id: String?=null )

data class AdminUser(var name: String? = null, var dob:String? = null, var gender:String? = null, var phone:String? = null, var reason:String? = null,var doctor:String? = null, var id:String? = null,var uid:String?=null, var id2:String?=null)

data class DocProfile(var name: String? = null, var spec: String? = null, var yoe:String? = null, var hosname: String? = null, var availability: String? = null,var contact: String? = null,var fee:String?=null,var city: String? = null, var id: String?=null)

data class DocPatient(var name: String? = null, var dob:String? = null, var gender:String? = null, var phone:String? = null, var reason:String? = null)