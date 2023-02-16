package com.exraion.beu.validator

import androidx.viewbinding.ViewBinding

interface ConstraintValidator<VB: ViewBinding> {

    fun VB.validate()

}