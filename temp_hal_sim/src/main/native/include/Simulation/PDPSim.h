#pragma once

#ifndef __FRC_ROBORIO__

#include "MockData/PDPData.h"
#include <memory>
#include <utility>
#include "CallbackStore.h"

namespace frc {
namespace sim {
class PDPSim {
 public:
  explicit PDPSim(int index) {
    m_index = index;
  }

  std::unique_ptr<CallbackStore> RegisterInitializedCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelPDPInitializedCallback);
    store->SetUid(HALSIM_RegisterPDPInitializedCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  bool GetInitialized() {
    return HALSIM_GetPDPInitialized(m_index);
  }
  void SetInitialized(bool initialized) {
    HALSIM_SetPDPInitialized(m_index, initialized);
  }

  std::unique_ptr<CallbackStore> RegisterTemperatureCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelPDPTemperatureCallback);
    store->SetUid(HALSIM_RegisterPDPTemperatureCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  double GetTemperature() {
    return HALSIM_GetPDPTemperature(m_index);
  }
  void SetTemperature(double temperature) {
    HALSIM_SetPDPTemperature(m_index, temperature);
  }

  std::unique_ptr<CallbackStore> RegisterVoltageCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelPDPVoltageCallback);
    store->SetUid(HALSIM_RegisterPDPVoltageCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  double GetVoltage() {
    return HALSIM_GetPDPVoltage(m_index);
  }
  void SetVoltage(double voltage) {
    HALSIM_SetPDPVoltage(m_index, voltage);
  }

  std::unique_ptr<CallbackStore> RegisterCurrentCallback(int channel, NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, channel, -1, callback, &HALSIM_CancelPDPCurrentCallback);
    store->SetUid(HALSIM_RegisterPDPCurrentCallback(m_index, channel, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  double GetCurrent(int channel) {
    return HALSIM_GetPDPCurrent(m_index, channel);
  }
  void SetCurrent(int channel, double current) {
    HALSIM_SetPDPCurrent(m_index, channel, current);
  }

  void ResetData() {
    HALSIM_ResetPDPData(m_index);
  }
 private:
  int m_index;
};
} // namespace sim
} // namespace frc
#endif // __FRC_ROBORIO__