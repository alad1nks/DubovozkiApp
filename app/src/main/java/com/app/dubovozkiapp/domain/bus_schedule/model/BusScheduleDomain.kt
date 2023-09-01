package com.app.dubovozkiapp.domain.bus_schedule.model

data class BusScheduleDomain(
    val moscowSchedule: OneDirectionScheduleDomain,
    val dubkiSchedule: OneDirectionScheduleDomain
)