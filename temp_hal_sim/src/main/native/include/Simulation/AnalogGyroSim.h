#pragma once

#ifndef __FRC_ROBORIO__

#include "MockData/AnalogGyroData.h"
#include <memory>
#include <utility>
#include "CallbackStore.h"

namespace frc {
namespace sim {
class AnalogGyroSim {
 public:
  explicit AnalogGyroSim(int index) {
    m_index = index;
  }

  std::unique_ptr<CallbackStore> RegisterAngleCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelAnalogGyroAngleCallback);
    store->SetUid(HALSIM_RegisterAnalogGyroAngleCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  double GetAngle() {
    return HALSIM_GetAnalogGyroAngle(m_index);
  }
  void SetAngle(double angle) {
    HALSIM_SetAnalogGyroAngle(m_index, angle);
  }

  std::unique_ptr<CallbackStore> RegisterRateCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelAnalogGyroRateCallback);
    store->SetUid(HALSIM_RegisterAnalogGyroRateCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  double GetRate() {
    return HALSIM_GetAnalogGyroRate(m_index);
  }
  void SetRate(double rate) {
    HALSIM_SetAnalogGyroRate(m_index, rate);
  }

  std::unique_ptr<CallbackStore> RegisterInitializedCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelAnalogGyroInitializedCallback);
    store->SetUid(HALSIM_RegisterAnalogGyroInitializedCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  bool GetInitialized() {
    return HALSIM_GetAnalogGyroInitialized(m_index);
  }
  void SetInitialized(bool initialized) {
    HALSIM_SetAnalogGyroInitialized(m_index, initialized);
  }

  void ResetData() {
    HALSIM_ResetAnalogGyroData(m_index);
  }
 private:
  int m_index;
};
} // namespace sim
} // namespace frc
#endif // __FRC_ROBORIO__
