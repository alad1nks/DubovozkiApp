package com.app.dubovozkiapp.domain.bus_schedule.model

data class OneDirectionScheduleDomain(
    val firstBus: Int,
    val departedBusList: List<DepartedBusDomain>,
    val busList: List<BusDomain>
)