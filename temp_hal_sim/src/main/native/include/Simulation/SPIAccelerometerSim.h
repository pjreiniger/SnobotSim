#pragma once

#ifndef __FRC_ROBORIO__

#include "MockData/SPIAccelerometerData.h"
#include <memory>
#include <utility>
#include "CallbackStore.h"

namespace frc {
namespace sim {
class SPIAccelerometerSim {
 public:
  explicit SPIAccelerometerSim(int index) {
    m_index = index;
  }

  std::unique_ptr<CallbackStore> RegisterActiveCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelSPIAccelerometerActiveCallback);
    store->SetUid(HALSIM_RegisterSPIAccelerometerActiveCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  bool GetActive() {
    return HALSIM_GetSPIAccelerometerActive(m_index);
  }
  void SetActive(bool active) {
    HALSIM_SetSPIAccelerometerActive(m_index, active);
  }

  std::unique_ptr<CallbackStore> RegisterRangeCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelSPIAccelerometerRangeCallback);
    store->SetUid(HALSIM_RegisterSPIAccelerometerRangeCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  int GetRange() {
    return HALSIM_GetSPIAccelerometerRange(m_index);
  }
  void SetRange(int range) {
    HALSIM_SetSPIAccelerometerRange(m_index, range);
  }

  std::unique_ptr<CallbackStore> RegisterXCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelSPIAccelerometerXCallback);
    store->SetUid(HALSIM_RegisterSPIAccelerometerXCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  double GetX() {
    return HALSIM_GetSPIAccelerometerX(m_index);
  }
  void SetX(double x) {
    HALSIM_SetSPIAccelerometerX(m_index, x);
  }

  std::unique_ptr<CallbackStore> RegisterYCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelSPIAccelerometerYCallback);
    store->SetUid(HALSIM_RegisterSPIAccelerometerYCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  double GetY() {
    return HALSIM_GetSPIAccelerometerY(m_index);
  }
  void SetY(double y) {
    HALSIM_SetSPIAccelerometerY(m_index, y);
  }

  std::unique_ptr<CallbackStore> RegisterZCallback(NotifyCallback callback, bool initialNotify) {
    auto store = std::make_unique<CallbackStore>(m_index, -1, callback, &HALSIM_CancelSPIAccelerometerZCallback);
    store->SetUid(HALSIM_RegisterSPIAccelerometerZCallback(m_index, &CallbackStoreThunk, store.get(), initialNotify));
    return std::move(store);
  }
  double GetZ() {
    return HALSIM_GetSPIAccelerometerZ(m_index);
  }
  void SetZ(double z) {
    HALSIM_SetSPIAccelerometerZ(m_index, z);
  }

  void ResetData() {
    HALSIM_ResetSPIAccelerometerData(m_index);
  }
 private:
  int m_index;
};
} // namespace sim
} // namespace frc
#endif // __FRC_ROBORIO__
