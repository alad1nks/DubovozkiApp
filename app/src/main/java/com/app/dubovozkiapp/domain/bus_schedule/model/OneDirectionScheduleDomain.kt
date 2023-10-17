package com.app.dubovozkiapp.domain.bus_schedule.model

data class OneDirectionScheduleDomain(
    val firstBus: Int = 0,
    val busList: List<BusDomain>
)